myhappyscript()
{
  sudo kill $(sudo lsof -t -i:8080)
}

set -e
echo "Starting the carnage"
myhappyscript || true
echo "The program was obliterated"
