<template>
  <div
    class="flex p-2 my-4 space-x-3 bg-gray-100 rounded-15px group"
    :data-id="post.id"
    :data-creation-time="post.creationTime"
    :data-shared-by="post.sharedBy.username"
  >
    <img
      :src="post.sharedBy.imageURL"
      alt="image"
      class="object-cover w-10 h-10 rounded-2xl"
    />
    <div>
      <div>
        <span class="text-lg text-warm-gray-800 font-poppins">
          <NuxtLink
            :to="{
              name: 'socials-users-username',
              params: { username: `@${post.sharedBy.username}` },
            }"
            class="hover:underline"
          >
            {{ post.sharedBy.username }}
          </NuxtLink>
        </span>
        <span class="text-xs text-true-gray-500">
          {{ getTimeAgo }} at {{ getFormattedDate }}
        </span>
      </div>
      <div v-if="post.text" class="pb-2 italic">{{ post.text }}</div>
      <NuxtLink
        :to="{ name: 'folders-folderId', params: { folderId: post.folder.id } }"
        class="flex items-center space-x-2 border-b-2 border-transparent border-dotted group-hover:border-black"
      >
        <ig-icon name="folder" size="sm"></ig-icon>
        <div>
          <span class="text-lg text-gray-800">
            {{ post.folder.documentsCount }}
          </span>
          <span class="text-xs text-gray-500 font-poppins">file(s)</span>,
          <span class="text-lg text-gray-800">
            {{ post.folder.tags.length }}
          </span>
          <span class="text-xs text-gray-500 font-poppins">tag(s)</span>
        </div>
      </NuxtLink>
    </div>
  </div>
</template>

<script>
import * as vagueDate from 'vague-date'

export default {
  props: {
    post: { type: Object, required: true },
  },
  computed: {
    messageTime() {
      return Date.parse(this.post.creationTime)
    },
    getFormattedDate() {
      return this.$dayjs(this.messageTime).format('D MMM, YYYY')
    },
    getTimeAgo() {
      return vagueDate.get({ to: this.messageTime })
    },
  },
}
</script>
