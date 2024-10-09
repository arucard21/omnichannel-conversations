# Omni-channel conversations

## Configuring the Kubernetes cluster
You can configure the Kubernetes cluster by running the script that's provided for it, `./configure_k8s.sh`. However, you must have a local registry available for which there's a variable in the script that needs to be filled in. This local registry needs to be accessible with the provided hostname and port from both the host and the cluster. It will not only be used by Camel K but it is also where the backend will be published before being installed into the cluster.

## Testing Camel K
You can test the Camel K setup with the following command. It should build a new integration that outputs the text "Hello world from Camel K" to the console every 3 seconds.

```shell
kamel run kamel-test-integration.yaml --dev --namespace integrations
```

## Testing the Kafka cluster
First, run this commmand which starts a new pod with an interactive shell where you can type in messages that will be sent to the `omnichannel.conversations` topic in Kafka when you press Enter. It will give a warning before auto-creating the topic, if it does not exist yet, but this can be ignored.

```shell
kubectl -n integrations run kafka-producer -ti --image=quay.io/strimzi/kafka:0.43.0-kafka-3.8.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --bootstrap-server kafka-omnichannel-conversations-kafka-bootstrap:9092 --topic omnichannel.conversations
```

Then, run this command in a separate shell which starts a new pod that subscribes to the `omnichannel.conversations` topic in Kafka and prints out every message it receives. The `--from-beginning` parameter is used to immediately print out all the previous messages that were sent to this topic. If you only want to see new messages, you can leave out this parameter.

```shell
kubectl -n integrations run kafka-consumer -ti --image=quay.io/strimzi/kafka:0.43.0-kafka-3.8.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server kafka-omnichannel-conversations-kafka-bootstrap:9092 --topic omnichannel.conversations --from-beginning
```

With this setup, you can type a message in the interactive shell from the first command. Once you press Enter, it should be printed out in the shell from the second command. This shows that the message is successfully published to the Kafka topic and received by a subscriber.

## Testing Vitess
You should connect with Telepresence to make the pods directly accessible from the host. If you don't have it installed yet, you can follow the instructions at https://www.telepresence.io/docs/install/client. You also need to install the Traffic Manager, for which the instructions are available at https://www.telepresence.io/docs/install/manager. Though it should be as easy as `telepresence helm install`. You can then connect with Telepresence with `telepresence connect`. Every service in the cluster should now be accessible from the host with a hostname of the form `<service name>.<namespace name>`. But you still need to use the same port that is exposed for that service.

You can access the database server with the hostname `<vitess-cluster-vtgate-6e607aba>.datastore` where the part between brackets should be replaced with the service name for the vtgate service. It should look similar but has a random string at the end. You can find this name with `kubect get services --namespace datastore`. This service uses the default port for MySQL, 3306, and most clients already use that by default. The username to use is `user` and there is no password so it can be left empty. Accessing the database server like this should work with any MySQL-compatible client, including graphical clients. Once you're connected, you can inspect the existing databases or create your own.

You can also access the Vitess admin web interface at `http://<vitess-cluster-vtadmin-xxxxxxxx>.datastore:15000` where the part between brackets should be replaced with the service name for the vtadmin service.

## Deploying the backend
The backend is available in the subfolder "omnichannel-conversations-backend" so we need to open a shell in that folder. Then you can run the following commands to build the backend WAR file, create the Docker image, push it to the local registry, and install it in the cluster. For the local registry, `registry:5000` is used below but that should be replaced with whatever the correct hostname and port are for your local registry.

```shell
./gradlew build
docker build -t registry:5000/omnichannel-conversations-backend:0.0.1 .
docker push registry:5000/omnichannel-conversations-backend:0.0.1
helm install -n integrations omnichannel-conversations-backend chart/
```
