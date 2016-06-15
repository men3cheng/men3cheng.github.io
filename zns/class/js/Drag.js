function Drag(id){
	var _this=this;
	this.disX=null;
	this.disY=null;
	this.div=document.getElementById(id);
	this.div.onmousedown=function(ev){
		_this.fnDown(ev);
		return false;
	};
}
Drag.prototype.fnDown=function(ev){
	var _this=this;
	var oEvent=ev||event;
	this.disX=oEvent.clientX-this.div.offsetLeft;
	this.disY=oEvent.clientY-this.div.offsetTop;
	document.onmousemove=function(ev){
		_this.fnMove(ev);
	}
	document.onmouseup=function(){
		_this.fnUp();
	}
};
Drag.prototype.fnMove=function(ev){
	var oEvent=ev||event;
	this.div.style.left=oEvent.clientX-this.disX+'px';
	this.div.style.top=oEvent.clientY-this.disY+'px';
};
Drag.prototype.fnUp=function(){
	document.onmousemove=null;
	document.onmouseup=null;
}