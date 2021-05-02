from rest_framework import status
from rest_framework.parsers import MultiPartParser
from rest_framework.response import Response
from rest_framework.views import APIView

from . import models


class FileUploadView(APIView):
    parser_classes = (MultiPartParser,)

    def post(self, request, format=None):
        file_obj = request.FILES["file"]
        obj = models.Upload.objects.create(
            file=file_obj, user_id=request.POST.get("user_id")[0]
        )
        obj.save()
        return Response(data={"url": obj.file.url}, status=status.HTTP_200_OK)
