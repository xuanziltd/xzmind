#### JS代码　

```javascript
function test(){
	console.log("Hello world!");
}
 
(function(){
    var box = function(){
        return box.fn.init();
    };

    box.prototype = box.fn = {
        init : function(){
            console.log('box.init()');
			return this;
        },

		add : function(str){
			alert("add", str);
			return this;
		},

		remove : function(str){
			alert("remove", str);
			return this;
		}
    };
    
    box.fn.init.prototype = box.fn;
    window.box =box;
})();

var testBox = box();
testBox.add("jQuery").remove("jQuery");
```