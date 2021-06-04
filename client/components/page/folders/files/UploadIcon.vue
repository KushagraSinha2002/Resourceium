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
      <div
        class="flex justify-between px-2 pt-2 pb-1 mb-2 border border-gray-400 rounded-tl-15px rounded-br-15px"
      >
        <div>
          <div v-for="tag in folder.tags" :key="tag.id">{{ tag }}</div>
        </div>
        <div class="flex flex-col items-center">
          <v-swatches v-model="color" show-fallback></v-swatches>
          <div
            class="flex items-center justify-center w-6 h-6 rounded-full cursor-pointer hover:bg-black hover:bg-opacity-20"
            title="Delete File"
            @click.stop.prevent="addTag"
          >
            <ig-icon
              name="plus"
              variant="success"
              size="sm"
              class="!block mx-auto"
            ></ig-icon>
          </div>
        </div>
      </div>
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
        Drag & drop <span class="text-purple-800">images</span>,
        <span class="text-purple-800">videos</span> or any
        <span class="text-purple-800">file</span>
      </div>
      <div>
        <label for="files" class="cursor-pointer">
          or
          <span
            class="text-purple-800 underline md:no-underline md:hover:underline"
          >
            browse files</span
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
      color: '#A463BF',
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
    addTag() {
      console.log(this.color)
    },
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
          await this.$axios.$post(this.backendUploadURL, {
            name: filename,
            storageID: res1.storageID,
            size,
          })
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

<style scoped>
.custom-file-input::-webkit-file-upload-button {
  visibility: hidden;
  @apply appearance-none;
}

.custom-file-input::before {
  content: 'Add file';
  @apply cursor-pointer outline-none select-none;
}

.custom-file-input::after {
  content: 'or drop files here';
  @apply cursor-pointer outline-none select-none;
}
</style>
