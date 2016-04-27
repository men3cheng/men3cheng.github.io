//在全局和所有元素类型的父类型的原型中，封装相同的$方法
//专用于在各种情况下用选择器查询元素对象
//接收一个字符串格式的选择器作为参数
//返回找到的一个元素对象或多个元素对象的数组
window.$=HTMLElement.prototype.$=function(selector){
	//如果在全局直接调$，就在document范围内查询
	//否则，就在当前元素范围内查询
	var elems=
	(this==window?document:this).querySelectorAll(selector);
	if(!elems){//如果没找到结果
		return null;
	}else if(elems.length==1){//如果只找到一个结果
		return elems[0];
	}else{//否则
		return elems;
	}
}
window.onload=function(){
	/*顶部弹出菜单*/	
	var lis=$(".app_jd,.service");  
	for(var i=0;i<lis.length;i++){		
		lis[i].addEventListener("mouseover",function(){		
			this.$("."+this.className+">a").className="hover";		
			this.$("[id$='_items']").style.display="block";
		},false);
		lis[i].addEventListener("mouseout",function(){
			this.$("."+this.className+">a").className="";
			this.$("[id$='_items']").style.display="none";
		},false);
	}
}