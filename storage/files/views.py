from rest_framework import status
from rest_framework.parsers import MultiPartParser
from rest_framework.response import Response
from rest_framework.views import APIView

from . import models


class FileUploadView(APIView):
    parser_classes = (MultiPartParser,)

    def post(self, request, format=None, *args, **kwargs):
        file_obj = request.FILES["file"]
        obj = models.Upload.objects.create(
            file=file_obj,
            user_id=kwargs.get("user_id"),
            folder_id=kwargs.get("folder_id"),
            name=file_obj.name,
        )
        obj.save()
        return Response(data={"url": obj.file.url}, status=status.HTTP_200_OK)
