#!/bin/sh
LOCAL_DOCKER_REGISTRY="registry:5000"

helm repo add camel-k https://apache.github.io/camel-k/charts/
helm repo update
# Create namespace to use specifically for integrations
kubectl create namespace integrations
# Install Camel K
helm install camel-k --set platform.build.registry.address=$LOCAL_DOCKER_REGISTRY camel-k/camel-k --namespace integrations

# Install Strimzi Operator (helm version must be at least 3.8 to use OCI registry)
helm install strimzi-cluster-operator oci://quay.io/strimzi-helm/strimzi-kafka-operator --namespace integrations
# Create a Kafka cluster
kubectl apply -f kafka-cluster-single-node.yaml --namespace integrations

# Create namespace to use specifically for data persistence
kubectl create namespace datastore
# Install Vitess Operator (including CRDs)
kubectl apply -f vitess-operator.yaml --namespace datastore
# Create a minimal cluster for Vitess distributed database
kubectl apply -f vitess-minimal-cluster.yaml --namespace datastore
