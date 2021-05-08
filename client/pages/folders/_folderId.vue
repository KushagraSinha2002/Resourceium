<template>
  <div>
    <form
      enctype="multipart/form-data"
      novalidate
      @submit.prevent="handleSubmit"
    >
      <h1>Upload files</h1>
      <div class="dropbox">
        <input
          id="file"
          ref="inputBox"
          type="file"
          name="file"
          :disabled="isSaving"
          @change="handleFileChange"
        />
        <button type="submit" class="bg-blue-300 border border-black">
          Upload
        </button>
      </div>
    </form>
    {{ folder }}
  </div>
</template>

<script>
import filesize from 'filesize'
import { sleep } from '~/utils/sleep.js'

const STATUS_INITIAL = 0
const STATUS_SAVING = 1
const STATUS_SUCCESS = 2
const STATUS_FAILED = 3

export default {
  filters: {
    getFileSize(value) {
      return filesize(value)
    },
  },
  middleware: ['auth'],
  data() {
    return {
      folder: null,
      currentStatus: null,
      file: null,
    }
  },
  computed: {
    isInitial() {
      return this.currentStatus === STATUS_INITIAL
    },
    isSaving() {
      return this.currentStatus === STATUS_SAVING
    },
    isSuccess() {
      return this.currentStatus === STATUS_SUCCESS
    },
    isFailed() {
      return this.currentStatus === STATUS_FAILED
    },
  },
  mounted() {
    this.getFolderDetails()
    this.reset()
  },
  methods: {
    getFolderDetails() {
      const folderId = this.$route.params.folderId
      this.$axios.$get(`/folders/folder/${folderId}`).then((resp) => {
        this.folder = resp
      })
    },
    reset() {
      // reset form to initial state
      this.currentStatus = STATUS_INITIAL
      this.$refs.inputBox.value = null
    },
    save(formData) {
      // upload data to the server
      this.currentStatus = STATUS_SAVING
      this.$axios
        .$post(`/folders/upload-file/${this.folder.id}`, formData)
        .then((_resp) => {
          this.$addAlert({
            message: 'Uploaded file successfully',
            type: 'success',
            timeOut: 1000,
          })
        })
        .catch((_err) => {
          this.$addAlert({
            message: 'Error in uploading file',
            type: 'danger',
            timeOut: 1000,
          })
        })
      this.currentStatus = STATUS_SUCCESS
    },
    handleFileChange() {
      this.file = this.$refs.inputBox.files[0]
    },
    async handleSubmit() {
      // handle file changes
      const formData = new FormData()
      if (!this.file) return
      if (this.file.size > 2048000) {
        this.$addAlert({
          message: 'File size can not be above 2 MB',
          type: 'danger',
          timeOut: 1000,
        })
        this.reset()
        return
      }
      formData.append('file', this.file, this.file.name)
      // save it
      this.save(formData)
      this.reset()
      await sleep(500)
      this.getFolderDetails()
    },
  },
}
</script>
