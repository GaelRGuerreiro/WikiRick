from django.db import models

class CustomUser(models.Model):
	username = models.CharField(unique=True, max_length=50)
	encrypted_password = models.CharField(max_length=100)
	email = models.CharField(unique=True, max_length=200)

	def __str__(self):
		return self.username

class UserSession(models.Model):
	user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
	token = models.CharField(unique=True, max_length=20)

	def __str__(self):
		return str(self.user) + ' - ' + self.token
		
class FavoriteCharacter(models.Model):
	user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
	character_image_url = models.CharField(max_length=500)
	character_name = models.CharField(max_length=500)
	
	def to_obj(self):
		return {
		"id": self.id,
		"image_url": self.character_image_url,
		"name": self.character_name
		}
