myhappyscript1()
{
  sudo kill $(sudo lsof -t -i:8080)
}

myhappyscript2()
{
  sudo kill $(sudo lsof -t -i:80)
}

set -e
myhappyscript1 || true
myhappyscript2 || true
