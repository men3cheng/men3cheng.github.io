function LimitDrag(id){
	Drag.call(this,id);   //继承属性
}
for(var  i in Drag.prototype){
	LimitDrag.prototype[i]=Drag.prototype[i];
}
LimitDrag.prototype.fnMove=function(ev){
	var oEvent=ev||event;
	var l=oEvent.clientX-this.disX;
	var t=oEvent.clientY-this.disY;
	if(l<0){
		l=0;
	}else if(l>document.documentElement.clientWidth-this.div.offsetWidth){
		l=document.documentElement.clientWidth-this.div.offsetWidth;
	}
	this.div.style.left=l+'px';
	this.div.style.top=t+'px';
};