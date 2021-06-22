#!/bin/bash

localdir=$(pwd)
distDir=$localdir/dist
echo $distDir
function envEcho() {
  echo 'Select environment'
  echo '1)dev'
  echo '2)test'
  echo '9)prod'
}

function install() {
  mkdir -p $distDir
  for d in $localdir/services/*; do
    if [[ $d == *'commons'* ]]; then
      mvn clean -f $d install
    fi
  done
}

function compile() {
  mkdir -p $distDir
  for d in $localdir/services/*; do
    if [[ $d != *'commons'* ]]; then
      mvn -f $d -P$env -Dmaven.test.skip=true clean package war:war
      cp $d/target/*war $distDir
    fi
  done
}

envEcho
read line

case $line in
1)
  env=dev
  ;;
2)
  env=test
  ;;
9)
  env=prod
  ;;
*)
  echo EXIT
  exit
  ;;
esac

distDir=$distDir/$env
rm -fr $distDir
echo compile env $env
echo output folder $distDir
install
compile

echo done
read line
