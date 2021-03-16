## 封装Activity
[![](https://jitpack.io/v/liangke21/myActivity.svg)](https://jitpack.io/#liangke21/myActivity)
### 将其添加到存储库末尾的root build.gradle中
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
### 添加依赖项
```java
	dependencies {
	       implementation 'com.github.liangke21:myActivity:Tag'
	}

```

### 怎么使用
 * 继承BaseActivity