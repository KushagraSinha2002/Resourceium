import os
import uuid

from django.db import models
from django.utils.translation import gettext as _


def get_upload_directory(instance, filename):
    return os.path.join("uploads", instance.user_id, f"slug--{uuid.uuid4()}--{filename}")


class Upload(models.Model):
    file = models.FileField(
        help_text=_("The actual file that is uploaded"), upload_to=get_upload_directory
    )
    user_id = models.IntegerField(help_text=_("The id of the user this file belongs to"))

    def __str__(self):
        return f"{self.user_id}"
