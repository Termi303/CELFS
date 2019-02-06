myhappyscript()
{
  tokillem=$(sudo lsof -t -i:8080)
  sudo kill $(sudo lsof -t -i:8080)
}

set -e
echo ein
myhappyscript || true
echo zwei
sudo iptables -F
echo drei
(cd ~/JARFILE && exec nohup java -jar website-0.0.1-SNAPSHOT.jar &)
echo vier
