<?php


require "Connexion.php";



if(isset($_GET['username']) && isset($_GET['pass'])){


$username = strtolower(htmlspecialchars($_GET['username']));
$password = htmlspecialchars($_GET['pass']);
$hash = sha1($password);


$sth = $connexion->prepare("SELECT id_emp FROM employe WHERE nom_emp = :username AND hashPassword = :password");
$sth->execute(array(//Requete préparé permettant d'authentifier l'utilisateur
'username' => $username,
'password' => $hash
));
$result = $sth->fetch(PDO::FETCH_ASSOC);

if($result){
$token = md5(microtime(TRUE)*100000);//On génère un token ( avantage par rapport au password comme identifiant )
$req = $connexion->prepare('UPDATE employe SET token = :token, datetoken = NOW() WHERE id_emp = :id');
        $req->execute(array(
          'token' => $token,
		  'id' => $result['id_emp']
        ));
		
array_push($result, $token);
header('Content-type: application/json');
		echo json_encode(array('user'=>$result));
}else{
echo 'Nom d\'utilisateur ou mot de passe incorrect';
}
}else{
echo 'Parametres incorrects';
}


?>