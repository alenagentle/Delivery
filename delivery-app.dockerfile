FROM openjdk:17

CMD (rm -rf /app; mkdir /app) && \
cp -rf /workspace/app/target/dependency/BOOT-INF/lib /app/lib && \
cp -rf /workspace/app/target/dependency/META-INF /app/META-INF && \
cp -rf /workspace/app/target/dependency/BOOT-INF/classes/* /app && \
java -cp app:app/lib/* com.guavapay.delivery.DeliveryApplication