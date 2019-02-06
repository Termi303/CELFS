set -e
tokillem=$(sudo lsof -t -i:8080)
sudo kill $(sudo lsof -t -i:8080)
