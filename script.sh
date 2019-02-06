(tokillem=$(sudo lsof -t -i:8080) && if [ "&tokillem" != "" ]; then sudo kill $(sudo lsof -t -i:8080); fi)
