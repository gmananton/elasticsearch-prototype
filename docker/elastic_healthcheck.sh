#!/bin/bash

set -e

host="elasticsearch"
port="9200"
cmd="$@"

>&2 echo "==================================================="
>&2 echo "========> Checking elasticsearch is ready <========"
>&2 echo "==================================================="

until curl http://"$host":"$port"/_cluster/health; do
  >&2 echo "==========================================================="
  >&2 echo "========> Elasticsearch is unavailable - sleeping <========"
  >&2 echo "==========================================================="
  sleep 1
done

>&2 echo "==========================================================="
>&2 echo "========> Elasticsearch is up - executing command <========"
>&2 echo "==========================================================="

exec $cmd