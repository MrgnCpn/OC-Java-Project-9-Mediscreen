#!/bin/sh
cd ./EdgeMS/ems-eureka
./build.sh

cd ../../EdgeMS/ems-zuul
./build.sh

cd ../../EdgeMS/ems-admin
./build.sh

cd ../../ms-authentication
./build.sh

cd ../ms-patientAdmin
./build.sh