#!/bin/sh
LOCAL_DOCKER_REGISTRY="registry:5000"

helm repo add camel-k https://apache.github.io/camel-k/charts/
helm repo update
# Create namespace to use specifically for integrations
kubectl create namespace integrations
# Install Camel K
helm install camel-k --set platform.build.registry.address=$LOCAL_DOCKER_REGISTRY camel-k/camel-k --namespace integrations
## Testing Camel K
kamel run kamel-test-integration.yaml --dev --namespace integrations

# Install Strimzi Operator (helm version must be at least 3.8 to use OCI registry)
helm install strimzi-cluster-operator oci://quay.io/strimzi-helm/strimzi-kafka-operator --namespace integrations
# Create a Kafka cluster
kubectl apply -f kafka-cluster-single-node.yaml --namespace integrations

## Testing the Kafka cluster
# Create a command prompt where you can type in messages that will be received by a consumer (will give a warning before auto-creating the topic, if it does not exist yet. This warning can be ignored)
#kubectl -n integrations run kafka-producer -ti --image=quay.io/strimzi/kafka:0.43.0-kafka-3.8.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --bootstrap-server kafka-omnichannel-conversations-kafka-bootstrap:9092 --topic omnichannel.conversations
# Receive messages sent to Kafka topic
#kubectl -n integrations run kafka-consumer -ti --image=quay.io/strimzi/kafka:0.43.0-kafka-3.8.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server kafka-omnichannel-conversations-kafka-bootstrap:9092 --topic omnichannel.conversations --from-beginning

# Create namespace to use specifically for data persistence
kubectl create namespace datastore
# Install Vitess Operator (including CRDs)
kubectl apply -f vitess-operator.yaml --namespace datastore
# Create a minimal cluster for Vitess distributed database
kubectl apply -f vitess-minimal-cluster.yaml --namespace datastore
