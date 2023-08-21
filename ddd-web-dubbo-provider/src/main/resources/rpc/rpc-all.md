
# ddd-web-dubbo-provider



| Version | Update Time | Status | Author | Description |
|---------|-------------|--------|--------|-------------|
|1.0 |2023-08-21 17:00:00 |create |wenky |初始化|



## Add dependency

```
<dependency>
    <groupId>com.wenky.cola.ddd.web</groupId>
    <artifactId>ddd-web-dubbo-provider</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

```



## 

**URI:** /com.wenky.provider.dubbo.service.IHelloService

**Service:** com.wenky.provider.dubbo.service.IHelloService

**Protocol:** dubbo

**Author:** 

**Version:** 

### 


**Definition：** String getName()


**Description:** 




### 


**Definition：** Customer getByName(String name)


**Description:** 

**Invoke-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|String|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|id|java.lang.Long|No comments found.|-|
|createdAt|java.time.LocalDateTime|No comments found.|-|
|updatedAt|java.time.LocalDateTime|No comments found.|-|
|name|java.lang.String|No comments found.|-|
|age|java.lang.Integer|No comments found.|-|
|phone|java.lang.String|No comments found.|-|
|port|java.lang.String|No comments found.|-|


### 


**Definition：** DubboInvokeResult getWrapperByName(String name)


**Description:** 

**Invoke-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|String|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|java.lang.Integer|No comments found.|-|
|message|java.lang.String|No comments found.|-|
|responseTime|java.time.LocalDateTime|No comments found.|-|
|data|object|No comments found.|-|
|exception|Throwable|No comments found.|-|
|└─detailMessage|java.lang.String|No comments found.|-|
|└─cause|object|No comments found. $ref... self|-|
|└─stackTrace|java.lang.StackTraceElement[]|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─declaringClass|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─methodName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lineNumber|int|No comments found.|-|
|└─suppressedExceptions|java.util.List|No comments found.|-|


### 


**Definition：** void update(Customer customer)


**Description:** 

**Invoke-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|customer.id|java.lang.Long|false|No comments found.|-|
|customer.createdAt|java.time.LocalDateTime|false|No comments found.|-|
|customer.updatedAt|java.time.LocalDateTime|false|No comments found.|-|
|customer.name|java.lang.String|false|No comments found.|-|
|customer.age|java.lang.Integer|false|No comments found.|-|
|customer.phone|java.lang.String|false|No comments found.|-|
|customer.port|java.lang.String|false|No comments found.|-|



### 


**Definition：** DubboInvokeResult IOError()


**Description:** 


**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|java.lang.Integer|No comments found.|-|
|message|java.lang.String|No comments found.|-|
|responseTime|java.time.LocalDateTime|No comments found.|-|
|data|object|No comments found.|-|
|exception|Throwable|No comments found.|-|
|└─detailMessage|java.lang.String|No comments found.|-|
|└─cause|object|No comments found. $ref... self|-|
|└─stackTrace|java.lang.StackTraceElement[]|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─declaringClass|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─methodName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lineNumber|int|No comments found.|-|
|└─suppressedExceptions|java.util.List|No comments found.|-|


### 


**Definition：** DubboInvokeResult RuntimeError()


**Description:** 


**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|java.lang.Integer|No comments found.|-|
|message|java.lang.String|No comments found.|-|
|responseTime|java.time.LocalDateTime|No comments found.|-|
|data|object|No comments found.|-|
|exception|Throwable|No comments found.|-|
|└─detailMessage|java.lang.String|No comments found.|-|
|└─cause|object|No comments found. $ref... self|-|
|└─stackTrace|java.lang.StackTraceElement[]|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─declaringClass|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─methodName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lineNumber|int|No comments found.|-|
|└─suppressedExceptions|java.util.List|No comments found.|-|


### 


**Definition：** DubboInvokeResult BizError()


**Description:** 


**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|java.lang.Integer|No comments found.|-|
|message|java.lang.String|No comments found.|-|
|responseTime|java.time.LocalDateTime|No comments found.|-|
|data|object|No comments found.|-|
|exception|Throwable|No comments found.|-|
|└─detailMessage|java.lang.String|No comments found.|-|
|└─cause|object|No comments found. $ref... self|-|
|└─stackTrace|java.lang.StackTraceElement[]|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─declaringClass|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─methodName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lineNumber|int|No comments found.|-|
|└─suppressedExceptions|java.util.List|No comments found.|-|


### 


**Definition：** Integer timeout()


**Description:** 




## 

**URI:** /com.wenky.provider.dubbo.service.IHelloService

**Service:** com.wenky.provider.dubbo.service.IHelloService

**Protocol:** dubbo

**Author:** 

**Version:** 

### 


**Definition：** String getName()


**Description:** 




### 


**Definition：** Customer getByName(String name)


**Description:** 

**Invoke-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|String|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|id|java.lang.Long|No comments found.|-|
|createdAt|java.time.LocalDateTime|No comments found.|-|
|updatedAt|java.time.LocalDateTime|No comments found.|-|
|name|java.lang.String|No comments found.|-|
|age|java.lang.Integer|No comments found.|-|
|phone|java.lang.String|No comments found.|-|
|port|java.lang.String|No comments found.|-|


### 


**Definition：** DubboInvokeResult getWrapperByName(String name)


**Description:** 

**Invoke-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|String|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|java.lang.Integer|No comments found.|-|
|message|java.lang.String|No comments found.|-|
|responseTime|java.time.LocalDateTime|No comments found.|-|
|data|object|No comments found.|-|
|exception|Throwable|No comments found.|-|
|└─detailMessage|java.lang.String|No comments found.|-|
|└─cause|object|No comments found. $ref... self|-|
|└─stackTrace|java.lang.StackTraceElement[]|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─declaringClass|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─methodName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lineNumber|int|No comments found.|-|
|└─suppressedExceptions|java.util.List|No comments found.|-|


### 


**Definition：** void update(Customer customer)


**Description:** 

**Invoke-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|customer.id|java.lang.Long|false|No comments found.|-|
|customer.createdAt|java.time.LocalDateTime|false|No comments found.|-|
|customer.updatedAt|java.time.LocalDateTime|false|No comments found.|-|
|customer.name|java.lang.String|false|No comments found.|-|
|customer.age|java.lang.Integer|false|No comments found.|-|
|customer.phone|java.lang.String|false|No comments found.|-|
|customer.port|java.lang.String|false|No comments found.|-|



### 


**Definition：** DubboInvokeResult IOError()


**Description:** 


**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|java.lang.Integer|No comments found.|-|
|message|java.lang.String|No comments found.|-|
|responseTime|java.time.LocalDateTime|No comments found.|-|
|data|object|No comments found.|-|
|exception|Throwable|No comments found.|-|
|└─detailMessage|java.lang.String|No comments found.|-|
|└─cause|object|No comments found. $ref... self|-|
|└─stackTrace|java.lang.StackTraceElement[]|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─declaringClass|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─methodName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lineNumber|int|No comments found.|-|
|└─suppressedExceptions|java.util.List|No comments found.|-|


### 


**Definition：** DubboInvokeResult RuntimeError()


**Description:** 


**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|java.lang.Integer|No comments found.|-|
|message|java.lang.String|No comments found.|-|
|responseTime|java.time.LocalDateTime|No comments found.|-|
|data|object|No comments found.|-|
|exception|Throwable|No comments found.|-|
|└─detailMessage|java.lang.String|No comments found.|-|
|└─cause|object|No comments found. $ref... self|-|
|└─stackTrace|java.lang.StackTraceElement[]|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─declaringClass|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─methodName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lineNumber|int|No comments found.|-|
|└─suppressedExceptions|java.util.List|No comments found.|-|


### 


**Definition：** DubboInvokeResult BizError()


**Description:** 


**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|code|java.lang.Integer|No comments found.|-|
|message|java.lang.String|No comments found.|-|
|responseTime|java.time.LocalDateTime|No comments found.|-|
|data|object|No comments found.|-|
|exception|Throwable|No comments found.|-|
|└─detailMessage|java.lang.String|No comments found.|-|
|└─cause|object|No comments found. $ref... self|-|
|└─stackTrace|java.lang.StackTraceElement[]|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─declaringClass|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─methodName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─fileName|java.lang.String|No comments found.|-|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lineNumber|int|No comments found.|-|
|└─suppressedExceptions|java.util.List|No comments found.|-|


### 


**Definition：** Integer timeout()


**Description:** 




### 


**Definition：** CompletableFuture<Customer> getByNameAsync(String name)


**Description:** 

**Invoke-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
|name|String|false|No comments found.|-|

**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
|id|java.lang.Long|No comments found.|-|
|createdAt|java.time.LocalDateTime|No comments found.|-|
|updatedAt|java.time.LocalDateTime|No comments found.|-|
|name|java.lang.String|No comments found.|-|
|age|java.lang.Integer|No comments found.|-|
|phone|java.lang.String|No comments found.|-|
|port|java.lang.String|No comments found.|-|


