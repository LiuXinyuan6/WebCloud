function disableTest(){
    for(var i=0;i<document.getElementsByClassName("moco-form-control").length;i++){
        document.getElementsByClassName("moco-form-control")[i].disabled="";
    }
    document.getElementById("save").style="";
    }
