$(function(){
	/*topbar_nav效果*/
	var topbavnava=$('#topbar .topbar_nav a');
	topbavnava.hover(function(){
		$(this).addClass("active");
	},function(){
		$(this).removeClass("active");
	})
	
	/*topbarcart效果*/
	var topbarcart=$('.topbar_cart');
	var cartmenu=$('.topbar_cart .cart_meau');
	topbarcart.hover(function(){
		$(this).find('.cartmini').addClass('active');
		cartmenu.slideDown(300);
	},function(){
		$(this).find('.cartmini').removeClass('active');
		cartmenu.slideUp(300);
	})
	
	/*登陆效果*/
	var link=$('.topbar_info .link');
	link.hover(function(){
		$(this).css('color','#fff');
	},function(){
		$(this).css('color','#B0B0B0');
	})
	
	/*滑过出现下拉框效果*/
	var all=$('.header_nav .list');
	var list=$('.header_nav .list .list_item');
	var list_a=$('.header_nav .list .list_item a');
	var children_box=$('.header_nav .items_childen');
	var children_list=$('.header_nav .items_childen ul');
	list.mouseover(function(){
		var index=$(this).index()-1;	/*获取的ul下标是从0开始的，获得的li位置是从1开始的，减去1就对上了(第1个数的下标是0)*/	
		$(this).find('a').addClass('on');
		$(this).siblings().find('a').removeClass('on');
		children_box.stop().slideDown(600);
		children_list.eq(index).show().siblings().hide();  /*ul.eq(0),显示第一ul，其他的隐藏*/
	})
	all.mouseout(function(){
		children_box.stop().slideUp(600);
		list_a.removeClass('on');
	})
	children_box.mouseover(function(){
		$(this).stop().css('display','block');
	})
	children_box.mouseout(function(){
		children_box.stop().slideUp(600);
	})
	
	/*home_nav效果*/
	var homelist=$('.home_nav .home_list .list_item');
	var homespan=$('.home_nav .home_list .list_item .list_item_child span');
	
	homelist.hover(function(){
		$(this).addClass('a');
		$(this).find('.list_item_child').css({'opacity':'1','boxShadow':'0 2px 10px rgba(0,0,0,0.15)'}).show();
	},function(){
		$(this).removeClass('a');
		$(this).find('.list_item_child').hide();
	})
	
	homespan.hover(function(){
		$(this).addClass('b');
	},function(){
		$(this).removeClass('b');		
	})
	
	/*home轮播图*/
	var $lis=$('.home .lunbo li');
	var $lists=$('.home .tab span');
	var $ban=$('.home');
	var $prev=$('.home .prev');
	var $next=$('.home .next');
	$next.hover(function(){
		$(this).addClass('active');
	},function(){
		$(this).removeClass('active');
	})
	$prev.hover(function(){
		$(this).addClass('active');
	},function(){
		$(this).removeClass('active');
	})
	$next.click(function(){
		change();
	})
	$prev.click(function(){
		var $listprev = $lists.filter('.active').prev();/*prev() 获得匹配元素集合中每个元素紧邻的前一个同胞元素*/
		$listprev=$index==1?$lists.last():$listprev;
		$listprev.trigger('click'); /*trigger('click'):触发click()事件*/
	})
	var $len = $lists.size();
	var $index =0;
	$ban.hover(function(){
		clearInterval($timer); /*取消由 setInterval() 设置的 时长。*/
	},function(){
		$timer=setInterval(change,3000);
	})
	$lists.click(function(){
		  $index=$(this).index();
		  change();		  
	})
	$timer=setInterval(change,3000);
	function change(){
		$lists.eq($index).addClass('active').siblings().removeClass('active');
		$lis.eq($index).stop().fadeIn(500).siblings().stop().fadeOut(500);
		$index++;
		$index=$index%$len;
	}
	change();
	
	/*三张图阴影效果*/
	var hhimg=$('.home_good_pic ul li');
	hhimg.hover(function(){
		$(this).addClass('on');
	},function(){
		$(this).removeClass('on');
	})

	/*小米单品滚动*/
    var dp_lis = $('.dp_lunbo ul');
	var dpleft = $('.dp_top .dp_top_right .left');
	var dpright = $('.dp_top .dp_top_right .right');
	var dpbtn = $('.dp_top .dp_top_right');
	dpbtn.hover(function(){
	    clearInterval(timer1);
    },function(){
	    timer1=setInterval(change1,3000);
    })
	dpleft.click(function(){
		dp_lis.animate({'left':'0px'},300);
			aleft();
   		 })
	dpright.click(function(){
		dp_lis.animate({'left':'-1226px'},300);		
		aright();
	})
	var onoff=true;
	timer1=setInterval(change1,3000);
	function change1(){
		if(onoff){
			dp_lis.animate({'left':'-1226px'},300);
			onoff=false;
			aright();
		}else{
			dp_lis.animate({'left':'0px'},300);
			onoff=true;		
			aleft();
		}	
	}
	function aleft(){
		dpleft.addClass('bb').removeClass('cc');
		dpright.addClass('cc').removeClass('bb');
	}
	function aright(){
		dpright.addClass('bb').removeClass('cc');
		dpleft.addClass('cc').removeClass('bb');
	}

    /*智能硬件阴影+位移效果*/
	var zn_leftitems=$('.zn_body_left ul li');
	zn_leftitems.hover(function(){
		$(this).addClass('bshadow');
	},function(){
		$(this).removeClass('bshadow');
	})
    /*共通的函数*/
	function changeBshadow(obj){
		obj.hover(function(){
			$(this).addClass('bshadow');
			$(this).find('.dapei-review').stop().animate({'height':'60px','opacity':'1'}).siblings().find('.dapei-review').css({'height':'0px','opacity':'0'});
		},function(){
			$(this).removeClass('bshadow');
			$(this).find('.dapei-review').stop().animate({'height':'0px','opacity':'0'})
		})
	}
	var zn_rightitems=$('.zn_body_right ul li');
	changeBshadow(zn_rightitems);	

	var tab_item1=$('.dapei .dapei_body .dapei_body_left ul li');
	var tab_item2=$('.dapei .dapei_body .dapei_body_right ul li');
	changeBshadow(tab_item1);
	changeBshadow(tab_item2)
	
	function change2(obj1,obj2){
		obj1.mouseover(function(){
		var index = $(this).index();
		$(this).addClass('active').siblings().removeClass('active');
			obj2.eq(index).show().siblings().hide();
		})
	}	
	
	var smartnav=$('#dapei .dapei_header .dapei_header_right li');
	var smartlists=$('#dapei .dapei_body .dapei_body_right ul')
	change2(smartnav,smartlists)
	
	/*内容*/
	var $content_children=$('.content .content-children');
	changeBshadow($content_children);
	var $conindex = null;
	function changemove(obj1,obj2,obj3,obj4,obj5){
		var $conindex=0;
		obj1.click(function(){
			$conindex=$(this).index();
			$(this).addClass("active").siblings().removeClass('active');
			obj4.animate({'left':-$conindex*296+'px'});
		})	
		obj2.click(function(){
			if($conindex==0){
				return;
			}
			$conindex--
			obj1.eq($conindex).addClass("active").siblings().removeClass('active');
			obj4.animate({'left':-$conindex*296+'px'});
		})
		obj3.click(function(){
			if($conindex==3){
				return;
			}
			$conindex++
			obj1.eq($conindex).addClass("active").siblings().removeClass('active');
			obj4.animate({'left':-$conindex*296+'px'});
		})
		obj5.hover(function(){
			$(this).find('a').fadeIn(300);
		},function(){
			$(this).find('a').fadeOut(300);
		})
	}
	/*first*/
	var $contfrlist=$('.content .first .content-list');
	var $contfrlis=$('.content .first .tab span');
	var $confrlift=$('.content .first .left');
	var $confrright=$('.content .first .right')
	var $confirst=$('.content .first')
	changemove($contfrlis,$confrlift,$confrright,$contfrlist,$confirst);
	/*second*/
	var $contselist=$('.content .second .content-list');
	var $contselis=$('.content .second .tab span');
	var $conselift=$('.content .second .left');
	var $conseright=$('.content .second .right')
	var $consecond=$('.content .second')
	changemove($contselis,$conselift,$conseright,$contselist,$consecond);
	/*third*/
	var $contthlist=$('.content .third .content-list');
	var $contthlis=$('.content .third .tab span');
	var $conthlift=$('.content .third .left');
	var $conthright=$('.content .third .right')
	var $conthird=$('.content .third')
	changemove($contthlis,$conthlift,$conthright,$contthlist,$conthird);
	/*four*/
	var $contfolist=$('.content .four .content-list');
	var $contfolis=$('.content .four .tab span');
	var $confolift=$('.content .four .left');
	var $conforight=$('.content .four .right')
	var $confour=$('.content .four')
	changemove($contfolis,$confolift,$conforight,$contfolist,$confour);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})
