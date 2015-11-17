echo "#update git"
cd /mnt/appointment/git/Reservation_THXX/
echo "git fetch..."
git checkout master
git fetch --all
echo "git update..."
git reset --hard origin/master
git pull origin master

echo "#set system env"
source /root/.bash_profile

echo "#deploy war"
echo "shutdown tomcat..."
/mnt/appointment/apache-tomcat-8.0.26/bin/shutdown.sh
sleep 5
echo "replace war..."
rm -rf /mnt/appointment/apache-tomcat-8.0.26/webapps/appointment*
cp /mnt/appointment/git/Reservation_THXX/target/appointment.war /mnt/appointment/apache-tomcat-8.0.26/webapps
echo "start tomcat..."
/mnt/appointment/apache-tomcat-8.0.26/bin/startup.sh
sleep 10

echo "#deploy jar"
echo "replace jar..."
cp -f /mnt/appointment/git/Reservation_THXX/target/appointment.jar /mnt/appointment/reminder
echo "restart cron"
service cron restart

echo "###Deployment Completed###"
