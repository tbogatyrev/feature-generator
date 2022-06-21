# feature-generator :star:

## Генерация .feature файлов на основе Swagger

1. Создать новый maven проект;
2. Добавить мавен-плагин [feature-generator-maven-plugin](#plugin);
3. [Добавить конфигурацию](#plugin_config);
4. Использовать команду mvn install.

## Maven plugin <a name="plugin"></a>
```
<plugin>
	<groupId>ru.lanit</groupId>
	<artifactId>feature-generator-maven-plugin</artifactId>
	<version>${feature-generator.version}</version>
	<configuration>
		<swaggerUrl>https://petstore.swagger.io/v2/swagger.json</swaggerUrl>
		<generateAdditionalFiles>true</generateAdditionalFiles>
		<pomValues>
			<groupId>org.example</groupId>
			<artifactId>test-feature-generator</artifactId>
			<projectVersion>1.0-SNAPSHOT</projectVersion>
		</pomValues>
	</configuration>
	<executions>
		<execution>
			<goals>
				<goal>generate</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
## Минимальная конфигурация плагина <a name="plugin_config"></a>

-**swaggerUrl** - ссылка на swagger сервиса;<br><p>
-**generateAdditionalFiles**, true/false - в случае true сгенерируется TestRunner, файлы .properties и обновиться pom.xml;<br><p>
-**pomValues** - атрибуты созданного проекта, необходимых для генерации pom.xml файла. (Параметр обязателен, в случае generateAdditionalFiles=true).<br><p>
