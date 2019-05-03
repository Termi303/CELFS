myhappyscript1()
{
  sudo kill $(sudo lsof -t -i:8080)
}

myhappyscript2()
{
  sudo kill $(sudo lsof -t -i:80)
}

myhappyscript3()
{
  sudo kill $(sudo lsof -t -i:443)
}

set -e
# myhappyscript1 || true
# myhappyscript2 || true
myhappyscript3 || true
