<template>
  <div
    :id="`file-${file.id}`"
    v-tippy="{ placement: 'top', arrow: true, animation: 'shift-toward' }"
    class="flex flex-col w-10/12 p-2 mx-auto bg-gray-100 ring-2 sm:ring-0 sm:w-full hover:ring-2 rounded-15px"
    :content="getFileDetails"
  >
    <div class="flex justify-between flex-none w-full">
      <a
        class="flex items-center justify-center rounded-full cursor-pointer hover:bg-black hover:bg-opacity-20 h-9 w-9"
        :href="getFileUrl"
        title="Download File"
      >
        <ig-icon name="download" variant="primary"></ig-icon>
      </a>
      <div
        class="flex items-center justify-center rounded-full cursor-pointer hover:bg-black hover:bg-opacity-20 h-9 w-9"
        title="Delete File"
        @click.stop.prevent="deleteFile"
      >
        <ig-icon name="trash" variant="danger"></ig-icon>
      </div>
    </div>
    <div
      class="flex flex-col items-center justify-center flex-grow border-b border-black border-dashed"
    >
      <img
        :src="require(`~/assets/images/extension/${getFileIcon}`)"
        :alt="`file-${file.id} icon`"
        class="inline p-3 w-28 h-28"
      />
      <div
        class="w-full font-mono text-sm text-center truncate"
        style="width: 25ch"
      >
        {{ file.name }}
      </div>
    </div>
    <div class="flex-none py-2">
      <div>
        <span class="text-sm">Uploaded:</span>
        <span class="font-semibold font-styled-code">
          {{ getFileDateOfUpload }}
        </span>
      </div>
      <div>
        <span class="text-sm">Filesize:</span>
        <span class="font-semibold font-styled-code">
          {{ filesize }}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import path from 'path'
import filesize from 'filesize'
import { cleanDoubleSlashes } from 'ufo'
import EXTENSIONS from '~/utils/extensions.json'
import { sleep } from '~/utils/sleep.js'

export default {
  props: {
    file: { type: Object, required: true },
  },
  data() {
    return {
      filesize: '0',
    }
  },
  fetch() {
    this.getFileSize()
  },
  computed: {
    getFileUrl() {
      return cleanDoubleSlashes(this.$config.storageServer + this.file.url)
    },
    getFileDateOfUpload() {
      return this.$dayjs(this.file.dateOfUpload).format('D MMM, H:mm')
    },
    getFileDetails() {
      const file = this.file
      let template = '<ol style="text-align: left">'
      template += `
        <li>Name: <strong>${file.name}</strong></li>
      `
      template += '</ol>'
      return template
    },
    getFileIcon() {
      for (let i = 0; i < EXTENSIONS.length; i++) {
        const ext = path.extname(this.file.name).substr(1)
        if (EXTENSIONS[i].extensions.includes(ext)) {
          return EXTENSIONS[i].name
        }
      }
      return 'default.png'
    },
  },
  methods: {
    getFileSize() {
      this.$axios.get(`${this.getFileUrl}`).then((resp) => {
        const size = filesize(resp.headers['content-length'])
        this.filesize = size
      })
    },
    deleteFile() {
      const deleteURL = cleanDoubleSlashes(
        this.$config.storageServer + this.file.deleteUrl
      )
      if (
        confirm(
          `This action can not be reversed. Delete file '${this.file.name}'?`
        )
      ) {
        this.$axios
          .$delete(deleteURL)
          .then(() => {
            this.$axios
              .$delete(`/documents/delete/${this.file.id}`)
              .then(async () => {
                await sleep(100)
                this.$emit('refreshFolder')
              })
              .catch(() => {
                this.$addAlert({
                  message: 'Some error',
                  type: 'danger',
                })
              })
          })
          .catch(() => {
            this.$addAlert({
              message:
                'There was some problem processing your request. Please try again later.',
              type: 'danger',
            })
          })
      }
    },
  },
}
</script>
