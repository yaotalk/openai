#!/bin/bash
yum install readline-devel pcre-devel openssl-devel gcc -y
yum-config-manager --add-repo https://openresty.org/yum/cn/centos/OpenResty.repo
yum install openresty -y

mkdir -p /deploy/open-resty
cd /deploy/open-resty
cp -r /home/conf /deploy/open-resty
cp -r /home/html /deploy/open-resty
mkdir logs

yum install libidn libidn-devel openresty-opm -y
opm install xiaooloong/ourls-resty
cp /home/luarocks-2.4.2.tar.gz /deploy/open-resty
tar zvxf luarocks-2.4.2.tar.gz
cd luarocks-2.4.2
./configure --prefix=/usr/local/openresty/luajit --with-lua=/usr/local/openresty/luajit/ --lua-suffix=jit --with-lua-include=/usr/local/openresty/luajit/include/luajit-2.1
make build && make install

/usr/local/openresty/luajit/bin/luarocks install net-url
/usr/local/openresty/luajit/bin/luarocks install router

cd ..
cp /home/lua-5.3.4.tar.gz /deploy/open-resty
tar zvxf lua-5.3.4.tar.gz
cd lua-5.3.4
make linux test
make && make install

echo 'export PATH=/usr/local/bin:$PATH' > /etc/profile.d/lua.sh
source /etc/profile

cd ..
cp /home/hashids.lua-1.0.5.tar.gz /deploy/open-resty
tar zvxf hashids.lua-1.0.5.tar.gz
cd hashids.lua-1.0.5
mv Makefile Makefile.bak
cp /home/Makefile /deploy/open-resty/hashids.lua-1.0.5
make && make install

cp /home/config.lua /usr/local/openresty/site/lualib/ourl
openresty -p /deploy/open-resty/
