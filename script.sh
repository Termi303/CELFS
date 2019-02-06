myhappyscript()
{
  sudo kill $(sudo lsof -t -i:8080)
}

somescript()
{
  nohup java -jar ~/JARFILE/website-0.0.1-SNAPSHOT.jar &
}

set -e
echo ein
myhappyscript || true
echo zwei
sudo iptables -F
echo drei
somescript >/dev/null
echo vier
