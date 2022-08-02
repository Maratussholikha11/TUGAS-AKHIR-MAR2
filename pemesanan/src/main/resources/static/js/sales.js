var productId = document.getElementById("id_product").value;
            alert("javascript")
            var lunas = document.getElementByName("payOff").value;
            alert(lunas);
            if(lunas == "Done"){
                var done = document.getElementByName("done");
                done.checked = true;
            }else if(lunas == "waiting"){
                var waiting = document.getElementByName("waiting");
                waiting.checked = true;
            }