JSONRPCClient
=============

JSONRPCClient is a simple and lightweight Android JSON RPC client library for accessing JSON RPC API. Feel free to use it all you want in your Android apps.


How to use it
-----

```java
try {
    JSONRPCClient jsonRpc = new JSONRPCClient();
            
    jsonRpc.setMethod("RetrieveData"); //set method name
            
    //set parameters
    jsonRpc.addParam("id",          "124");
    jsonRpc.addParam("lastUpdate",  "2014-01-01 00:00:00");
            
    //connect
    JSONObject jsonObj = jsonRpc.connect();
            
    //parse and process jsonObj
} catch (Exception e) { 

}
```
  
For more information, please visit my blog post [Simple JSON RPC Client for Android][2]

Developed By
------------
* Lorensius W. L. T - <lorenz@londatiga.net>

Website
-------
* [www.londatiga.net][1]

License
-------

    Copyright 2014 Lorensius W. L. T

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[1]: http://www.londatiga.net
[2]: http://www.londatiga.net/featured-articles/simple-json-rpc-client-for-android/