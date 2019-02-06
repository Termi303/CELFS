myhappyscript()
{
  tokillem=$(sudo lsof -t -i:8080)
  sudo kill $(sudo lsof -t -i:8080)
}
set -e
echo ein
myhappyscript || true
echo zwei
