echo "Making and publishing a file to itch"
echo "Starting the build script"
ant -q
echo "Finished building"
echo "Renaming file to itch sutable one"
cp build/jar/LD37-Build-*.jar ~/Documents/Butler/LD37.jar
echo "Starting buttler for"
mkdir ~/Documents/Butler/build
mkdir ~/Documents/Butler/build/natives
cd ~/Documents/Java\ Librarys/JInput
zip nat.zip *
mv nat.zip ~/Documents/Butler/build/natives
cd ~/Documents/Butler
mv LD37.jar build/LD37.jar
cp run.sh build/run.sh
cp .itch.toml build/.itch.toml
unzip ~/Documents/Butler/build/natives/nat.zip -d ~/Documents/Butler/build/natives/
./butler diff -v /dev/null ./build
./butler apply --signature patch.pwr.sig --inplace patch.pwr ~/.config/itch/apps/Ludum\ Dare\ 37/
echo "Finished patching with butler"
