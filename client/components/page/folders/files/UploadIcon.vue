<template>
  <form
    enctype="multipart/form-data"
    novalidate
    class="border"
    @submit.prevent="handleSubmit"
    @drop.prevent="addFile"
    @dragover.prevent
  >
    <h1>Upload files</h1>
    <div>
      <input
        id="files"
        ref="inputBox"
        multiple
        type="file"
        name="files"
        class="custom-file-input"
        @change="handleFileChange"
      />
      <button type="submit" class="bg-blue-300 border border-black">
        Upload
      </button>
    </div>
  </form>
</template>

<script>
import { cleanDoubleSlashes } from 'ufo'
import { sleep } from '~/utils/sleep'

export default {
  props: {
    folder: { type: Object, required: true },
  },
  data() {
    return {
      currentStatus: null,
      files: [],
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
  mounted() {
    this.reset()
  },
  methods: {
    reset() {
      // reset form to initial state
      this.$refs.inputBox.value = null
      this.files = []
    },
    async save(formData) {
      const filename = formData.get('file').name
      try {
        const res1 = await this.$axios.$post(this.serverUploadURL, formData)
        try {
          await this.$axios.$post(this.backendUploadURL, {
            name: filename,
            storageID: res1.storageID,
          })
        } catch {
          this.showError()
        }
      } catch {
        this.showError()
      }
    },
    handleFileChange() {
      this.files = this.$refs.inputBox.files
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
      if (!this.files.length) {
        this.$addAlert({
          message: 'Please select a file to upload',
          type: 'info',
          timeOut: 1000,
        })
        return
      }
      for (let i = 0; i < this.files.length; i++) {
        const formData = new FormData()
        const file = this.files[i]
        formData.append('file', file, file.name)
        // save it
        await this.save(formData)
      }
      await sleep(800)
      this.reset()
      this.$emit('refreshFolder')
    },
  },
}
</script>

<style scoped>
.custom-file-input::-webkit-file-upload-button {
  visibility: hidden;
}

.custom-file-input::before {
  border: 1px solid #999;
  border-radius: 3px;
  content: 'Select some files';
  cursor: pointer;
  display: inline-block;
  font-size: 10pt;
  font-weight: 700;
  outline: none;
  padding: 5px 8px;
  text-shadow: 1px 1px #fff;
  user-select: none;
  white-space: nowrap;
}

.custom-file-input:hover::before {
  border-color: #000;
}

.custom-file-input:active::before {
  background: #e3e3e3, #f9f9f9;
}
</style>
