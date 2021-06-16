<template>
  <NuxtLink
    v-tippy
    :to="{
      name: 'socials-discussions-id',
      params: { id: discussion.id },
    }"
    :content="getToolTip"
    class="flex items-center w-full duration-500 mx-auto transition-transform transform group hover:-translate-y-0.5 md:w-10/12 lg:w-full xl:w-10/12 2xl:w-full"
  >
    <div class="flex-none w-20 h-20 overflow-hidden rounded-l-lg">
      <img
        :src="discussion.imageURL"
        class="object-contain"
        :alt="`discussion-id-${discussion.id}`"
      />
    </div>
    <div
      class="flex items-center flex-grow px-2 py-2 space-x-4 transition-shadow duration-500 border border-l-0 border-gray-200 shadow-lg group-hover:shadow-2xl rounded-r-15px"
    >
      <div class="flex-1 overflow-hidden">
        <div class="text-xl text-gray-800" style="width: 12ch">
          {{ discussion.name }}
        </div>
        <div>
          <span class="text-warm-gray-600 font-poppins">Participants:</span>
          <span class="text-lg font-semibold font-code text-true-gray-700">
            {{ discussion.numParticipants }}
          </span>
        </div>
        <div>
          <span class="text-warm-gray-600 font-poppins">Posts:</span>
          <span class="text-lg font-semibold font-code text-true-gray-700">
            {{ discussion.numPosts }}
          </span>
        </div>
      </div>
      <div
        class="flex items-center justify-center flex-none flex-shrink-0 w-8 h-8 font-bold text-white rounded-r-lg font-poppins"
      >
        <ig-icon name="chevrons-right"></ig-icon>
      </div>
    </div>
  </NuxtLink>
</template>

<script>
export default {
  props: { discussion: { type: Object, required: true } },
  computed: {
    getToolTip() {
      let template = '<ol style="text-align: left">'
      template += `
        <li>Name: ${this.discussion.name}</li>
        <li>Description: ${this.discussion.description}</li>
      `
      template += '</ol>'
      return template
    },
  },
}
</script>
