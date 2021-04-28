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
          id="files"
          ref="files"
          type="file"
          multiple
          name="files"
          :disabled="isSaving"
          @change="handleFileChange"
        />
        <button type="submit" class="bg-blue-300 border border-black">
          Upload
        </button>
      </div>
    </form>
    <div>
      <div v-for="(file, index) in files" :key="index">
        {{ file.name }} - {{ file.size | getFileSize }}
      </div>
    </div>
  </div>
</template>

<script>
import filesize from 'filesize'

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
  data() {
    return {
      uploadedFiles: [],
      currentStatus: null,
      fileCount: 0,
      files: [],
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
    this.reset()
  },
  methods: {
    reset() {
      // reset form to initial state
      this.currentStatus = STATUS_INITIAL
      this.uploadedFiles = []
    },
    save(formData) {
      // upload data to the server
      this.currentStatus = STATUS_SAVING
      this.$uploadFile(window.localStorage.getItem('userId'), formData)
      this.currentStatus = STATUS_SUCCESS
    },
    handleFileChange() {
      this.files = this.$refs.files.files
    },
    handleSubmit() {
      const fileList = this.$refs.files.files
      // handle file changes
      const formData = new FormData()

      if (!fileList.length) return

      // append the files to FormData
      for (const propName in fileList) {
        if (typeof fileList[propName] === 'object') {
          formData.append('files', fileList[propName], fileList[propName].name)
        }
      }
      // save it
      this.save(formData)
    },
  },
}
</script>
