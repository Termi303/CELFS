myhappyscript()
{
  sudo kill $(sudo lsof -t -i:8080)
}

set -e
myhappyscript || true
