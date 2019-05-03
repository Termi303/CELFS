scriptfunction1()
{
  sudo kill $(sudo lsof -t -i:8080)
}

scriptfunction2()
{
  sudo kill $(sudo lsof -t -i:80)
}

scriptfunction3()
{
  sudo kill $(sudo lsof -t -i:443)
}

set -e
# scriptfunction1 || true
# scriptfunction2 || true
scriptfunction3 || true
