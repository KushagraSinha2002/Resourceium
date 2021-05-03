import os

from django.db import models
from django.utils.translation import gettext as _


def get_upload_directory(instance, filename):
    return os.path.join(
        "uploads",
        f"user--{instance.user_id}",
        f"folder--{instance.folder_id}",
        f"{filename}",
    )


class Upload(models.Model):
    file = models.FileField(
        help_text=_("The actual file that is uploaded."), upload_to=get_upload_directory
    )
    user_id = models.IntegerField(
        help_text=_("The id of the user this file belongs to.")
    )
    folder_id = models.IntegerField(
        help_text=_("The id of the folder this file belongs to.")
    )
    name = models.TextField(help_text=_("The name of the file that was uploaded."))

    def __str__(self):
        return f"{self.user_id}"
