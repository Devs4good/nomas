
const buildPhotoObjectFromData = (item) => {
    console.log("item", item);
    return {
        src: `${!!item.foto ? item.foto : './unknown.png'}`,
        thumbnail: item.foto,
        thumbnailWidth: 320,
        thumbnailHeight: 174,
        caption: item.situacion,
        tags: [
            { value: item.nombre, title: "NAME" },
            { value: `${!!item.fecha ? 'ausente desde ' + item.fecha : ''}`, title: "DATE" },
            { value: `${item.edad} AÑOS`, title: "EDAD" }
        ],
    }
}

export default buildPhotoObjectFromData;