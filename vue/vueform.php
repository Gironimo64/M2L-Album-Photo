<!DOCTYPE html>
<html>
<body>
<form action="./?action=upload" method="POST" enctype="multipart/form-data">
  Légende photo </br>
  <textarea name="legende" id="legende" rows="2" cols="50"></textarea> </br>
  Sélectionner l'image dans vos dossier:
  <input type="file" name="fileToUpload" id="fileToUpload">
  <input type="submit" value="Upload Image" name="submit">
</form>

</body>
</html>