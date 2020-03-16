docker run -d \
-p 8080:8080 \
-v /yeming.gao/java/2b_sit/logs/:/yeming.gao/java/2b_sit/logs/ \
-v /yeming.gao/static/images/:/yeming.gao/static/images/ \
--name=2b-site_8080 \
2b-site:1.0.0
