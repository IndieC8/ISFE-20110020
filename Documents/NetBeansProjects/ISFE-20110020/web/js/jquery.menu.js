/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){

    // Tabs
    $('#tabs').tabs();
	

    // Dialog			
    $('#dialog').dialog({
        autoOpen: false,
        width: 600,
        buttons: {
            "Ok": function() { 
                $(this).dialog("close"); 
            }, 
            "Cancel": function() { 
                $(this).dialog("close"); 
            } 
        }
    });
    
    // Dialog Link
    $('#dialog_link').click(function(){
        $('#dialog').dialog('open');
        return false;
    });
    
								
});

$(function(){
    //hover states on the static widgets
    $('#dialog_link, ul#icons li').hover(
    function() {
        $(this).addClass('ui-state-hover');
    }, 
    function() {
        $(this).removeClass('ui-state-hover');
    }
);
				
});

$(function(){

    // Slider
    $('#slider').slider({
        range: true,
        values: [17, 67]
    });
});


