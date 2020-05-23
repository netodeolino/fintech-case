#!/bin/bash

set -e

exec java -jar /var/picpay/users/users.jar
exec java -jar /var/picpay/transaction/transaction.jar