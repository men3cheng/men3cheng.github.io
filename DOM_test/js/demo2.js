/*创建一个select数组 */
function createSelect(arr){
	var sel=document.createElement("select");   //创建一个select元素；
	sel.add(new Option("-请选择-",-1));   //在select中添加一个option,内容为"-请选择-"，值为-1；
	for(var i=0;i<arr.length;i++){    //遍历arr中的每一个对象；
	    //新建一个option，将内容设置为当前对象的name,值设为当前对象的id,并将其添加到select中；
		sel.add(new Option(arr[i].name,arr[i].id)); 
	}	
	sel.onchange=function(){    //为当前select绑定onchange事件处理函数；
		while(this!=this.parentNode.lastChild){    //从最后一个节点开始，删除当前select之后的所有节点；
			this.parentNode.removeChild(this.parentNode.lastChild);
		}
		var i=this.selectedIndex;    //获取当前select选中项的下标；
		var cate=arr[i-1];    //在创建当前select元素的数组(arr)中找对应位置的对象;
		if(i!=0&&cate.children){    //如果选中对象的children属性有效;
			createSelect(cate.children);    //创建下级select,再次调用createSelect,传入当前对象的children;
		}
	}
	document.getElementById("category").appendChild(sel);    //找到id为category的div，将select追加到div下;
}
window.onload=function(){
	createSelect(data);
}
