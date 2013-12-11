JADT
====

#### Just Another Data Trapshoot.

Java algebraic data types using the fantasy-land specification. 

### Option

Example of a Option data type and mapping over the value.

```java
Option.of(1).map(new Function1<Integer, Integer>() {
	public Integer apply(Integer x) {
		return x + 1;
	}
});
```
