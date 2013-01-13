!function($){
	  $(".temp").click(function(){
		  $("#images").empty();
		  $("<img/>").attr("src", "../img/ajax-loader.gif").appendTo("#images");
	    var id=$(this).text();
	    $.getJSON("http://omdbapi.com/?"
	    		, {i: id}
	    		, function(data){
	    			$("#images").empty();
	    			$("<img/>").attr("src", data.Poster).addClass("img-polaroid").appendTo("#images");
	    		});
	    
	  });
	}(window.jQuery);
