<template>
  <form
    class="flex flex-col"
    enctype="multipart/form-data"
    novalidate
    @submit.prevent="handleSubmit"
    @drop.prevent="addFile"
    @dragover.prevent
  >
    <div
      class="p-3 text-center border-2 border-b-0 border-gray-400 border-dashed rounded-t-15px font-poppins"
    >
      <input
        id="files"
        ref="inputBox"
        multiple
        type="file"
        name="files"
        class="hidden"
        @change="handleFileChange"
      />
      <div class="mx-auto text-xl md:text-3xl sm:w-9/12">
        Drag & drop <span class="text-purple-800">Images</span>,
        <span class="text-purple-800">Videos</span> or any
        <span class="text-purple-800">File</span>
      </div>
      <div>
        <label for="files" class="cursor-pointer">
          or
          <span
            class="text-purple-800 underline md:no-underline md:hover:underline"
            ><strong> Browse Files</strong></span
          >
          on your device
        </label>
      </div>
      <div class="w-full mx-auto md:w-9/12">
        <span v-if="!uploading">Selected</span>
        <span v-else>Uploading</span>
        <span class="text-xl text-purple-800">{{ files.length }}</span> files
      </div>
    </div>
    <base-loading-button
      type="submit"
      text="Upload"
      :loading="uploading"
      :disabled="uploading"
      class="w-full px-3 py-2 mx-auto text-lg font-semibold bg-indigo-600 sm:px-4 md:text-xl text-purple-50 hover:ring focus:outline-none hover:ring-red-300 !transform-none ring-0 !rounded-t-none"
    >
    </base-loading-button>
  </form>
</template>

<script>
import { cleanDoubleSlashes } from 'ufo'
import fileSize from 'filesize'

export default {
  props: {
    folder: { type: Object, required: true },
  },
  data() {
    return {
      uploading: false,
      files: [],
      uploadPercentage: 0,
    }
  },
  computed: {
    serverUploadURL() {
      return cleanDoubleSlashes(
        `${this.$config.storageServer}/files/upload/${this.$auth.user.id}/${this.folder.id}`
      )
    },
    backendUploadURL() {
      return cleanDoubleSlashes(`/documents/upload/${this.folder.id}`)
    },
  },
  watch: {
    // uploadPercentage(newVal) {
    //   console.log(newVal)
    // },
  },
  mounted() {
    this.reset()
  },
  methods: {
    reset() {
      // reset form to initial state
      this.$refs.inputBox.value = null
      this.files = []
    },
    getFilesize(size) {
      return fileSize(size)
    },
    async save(formData) {
      const filename = formData.get('file').name
      const size = formData.get('file').size
      try {
        const res1 = await this.$axios.$post(this.serverUploadURL, formData)
        try {
          await this.$axios.$post(
            this.backendUploadURL,
            {
              name: filename,
              storageID: res1.storageID,
              size,
            },
            {
              onUploadProgress: function (progressEvent) {
                this.uploadPercentage = parseInt(
                  Math.round((progressEvent.loaded / progressEvent.total) * 100)
                )
              }.bind(this),
            }
          )
        } catch {
          this.showError()
        }
      } catch {
        this.showError()
      }
    },
    handleFileChange() {
      const files = this.$refs.inputBox.files
      for (const file of files) {
        this.files.push(file)
      }
    },
    showError() {
      this.$addAlert({
        message: 'Error in uploading file',
        type: 'danger',
        timeOut: 1000,
      })
    },
    addFile(e) {
      const droppedFiles = e.dataTransfer.files
      if (!droppedFiles) return
      ;[...droppedFiles].forEach((f) => {
        this.files.push(f)
      })
    },
    async handleSubmit() {
      // handle file changes
      this.uploading = true
      if (!this.files.length) {
        this.$addAlert({
          message: 'Please select a file to upload',
          type: 'info',
          timeOut: 1000,
        })
        this.uploading = false
      } else {
        const len = this.files.length
        for (let i = 0; i < len; i++) {
          const formData = new FormData()
          const file = this.files.pop()
          formData.append('file', file, file.name)
          // save it
          await this.save(formData)
          this.$emit('refreshFolder')
        }
        this.reset()
      }
      this.uploading = false
    },
  },
}
</script>
