function validasi_input(form){
		  var price = document.getElementById("harga").value;
			var qty = document.getElementById("quantity").value;
			var dp = document.getElementById("dp").value;
			var bayar = price * qty;
			var jmlDp = 0;
			var prc = 0.30;
			var jmlDp = bayar * prc;

			if(dp<jmlDp){
				alert("Total biaya : " + bayar + "\nMinimal DP sebesar " + jmlDp);
				return false;

			}

			var S = document.getElementById("jmlS").value;
			var M = document.getElementById("jmlM").value;
			var L = document.getElementById("jmlL").value;
			var XL = document.getElementById("jmlXL").value;
			var XXL = document.getElementById("jmlXL").value;
			var jmlU= S + M + L + XL + XXL;
			if( jmlU < qty){
				alert("Jumlah ukuran kurang!);
				return false;
			}else if(jmlU > qty){
				alert("Jumlah ukuran kelebihan!);
				return false;
			}

			return true;
		}