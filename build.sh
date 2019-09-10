#!/bin/bash


product=kepler-generic-client
outdir=output/$product
jar=$product.jar
target=target/$jar

# mvn打包
if [ ! -f $target ];then
    mvn clean install package -Dmaven.test.skip=true -U -e
fi

##########################################
# 打包成可执行的环境
##########################################

if [ -d output ];then
    rm -rf output
fi

mkdir -p $outdir/properties

cp -r $target $outdir/
cp -r src/main/resources/*.properties $outdir/properties/
cp -r src/main/resources/*.conf $outdir/properties/

# gen run.sh
cp -r run.sh $outdir/
cp -r reqpacks.txt $outdir/
sed -i "" "s/target\///g" $outdir/run.sh

# 压缩成zip包
cd output; zip -r $product.zip $product/*; cd ..

echo "!!!BUILD SUCCESS!!!"
