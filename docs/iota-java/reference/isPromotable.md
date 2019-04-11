
# [isPromotable](https://github.com/iotaledger/iota-java/blob/dev/jota/src/main/java/jota/IotaAPI.java#L1360)
 boolean isPromotable([Transaction](https://github.com/iotaledger/iota-java/blob/dev/jota/src/main/java/jota/model/Transaction.java) tail)

Checks if a transaction hash is promotable
> **Important note:** This API is currently in Beta and is subject to change. Use of these APIs in production applications is not supported.

## Input
| Parameter       | Type | Required or Optional | Description |
|:---------------|:--------|:--------| :--------|
| tail | [Transaction](https://github.com/iotaledger/iota-java/blob/dev/jota/src/main/java/jota/model/Transaction.java) | Required | the transaction we want to promote |
    
## Output
| Return type | Description |
|--|--|
| boolean  | true if it is, otherwise false |

## Exceptions
| Exceptions     | Description |
|:---------------|:--------|
| [ArgumentException](https://github.com/iotaledger/iota-java/blob/dev/jota/src/main/java/jota/error/ArgumentException.java) | when we can't get the consistency of this transaction |


 ## Example
 
 ```Java
 IotaAPI iotaAPI = new IotaAPI.Builder().build();

try { 
    boolean response = iotaAPI.isPromotable("tail");
} catch (ArgumentException e) { 
    // Handle error
    e.printStackTrace(); 
}
 ```
